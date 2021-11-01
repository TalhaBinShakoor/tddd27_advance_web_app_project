package org.liu.master.cs.apimonitor.repository;

import org.hibernate.reactive.mutiny.Mutiny;
import org.hibernate.reactive.stage.Stage;
import org.liu.master.cs.apimonitor.domain.BaseModel;
import org.hibernate.reactive.stage.Stage.SessionFactory;
import org.liu.master.cs.apimonitor.domain.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static javax.persistence.Persistence.createEntityManagerFactory;


/**
 * @author  Mutaz Hussein Kabashi
 * @version 1.0
 */
public abstract class  DataBaseRepository <T extends BaseModel>{

    protected static  SessionFactory factory ;
    protected final Class<T> clazz = getModelType();



    static final Logger LOGGER = LoggerFactory.getLogger(DataBaseRepository.class);


    //FIXME static variable Factory insitlaied more than once
    public void init(String persistenceUnitName){
        try{
            if(factory==null) {
                System.out.println("here1");
                factory = createEntityManagerFactory(persistenceUnitName)
                        .unwrap(SessionFactory.class);
            }
            System.out.println("here2");
        }catch (Exception ex){
            LOGGER.error("insertion operation failed with the following exception ", ex.getCause());
        }
    }


    /**
     * This Method fetchs all records from the database (work like Select * from tablename)
     * @return fetched records from the database as a list of Java Objects (POJO)
     */
      public  List<T> findAll(){
        // obtain a reactive session
        LOGGER.debug("get all web services from db ");
          List<T> result  = new ArrayList<>();

          //FIXME we should use generics
          factory.withSession(
          session -> session.createQuery(getFindAllQuery(), clazz).getResultList()
                              .thenAccept(r ->result.addAll(r))
                      // wait for it to finish
              ).toCompletableFuture().join();
          LOGGER.debug("get all web services from db2 ");
        return result;

    }

    /**
     * abstarct method, which should be implemented by the subclass to specify the SqlQuery for FindAll
     * @return FindAll's Sql query
     */
    public abstract String getFindAllQuery();

    /**
     * abstarct method , which we use to specify the Classs of the Model/Pojo
     * FIXME we should use Generic instead
     */
     public abstract Class<T>  getModelType();

    /**
     * This Method fetchs all records from the database using the passed  query
     * @return fetched records from the database as a list of Java Objects (POJO)
     *
     * @param  JPA query to be used to fetch data from the database
     */
    public  List<T> findByQuery(String query){
        LOGGER.debug("get records from db  based on JPA query");
        List<T> result  = new ArrayList<>();
        factory.withSession(
                session -> session.createQuery(query, clazz).getResultList()
                        .thenAccept(r ->result.addAll(r))
                // wait for it to finish
        ).toCompletableFuture().join();
        LOGGER.debug("get records from db  based on JPA query finished successfully ");
        return result;

    }

    /**
     * This Method fetchs all records from the database using the passed simple query
     * with one parameter.
     * @return fetched records from the database as a list of Java Objects (POJO)
     *
     * @param  JPA query to be used to fetch data from the database
     * @param  JPA Query parameter (where condition)
     * @param  value paramter's value
     */
    public  List<T> findByQueryAndParameter(String query,String parameter,Object value){
        LOGGER.debug("get records from db  based on JPA query");
        List<T> result  = new ArrayList<>();
        factory.withSession(
                session -> session.createQuery(query, clazz).setParameter(parameter,value)
                        .getResultList()
                        .thenAccept(r ->result.addAll(r))
                // wait for it to finish
        ).toCompletableFuture().join();
        LOGGER.debug("get records from db  based on JPA query finished successfully ");
        return result;

    }

    /**
     * Insert a Record (java Object POJO) into Databse
     * @param record a Java object to be instered into database
     */
    public  void insert(T record){
        LOGGER.debug("insert new record " + record);

        factory.withTransaction(
                (session, tx) -> session.persist(record)
                        .exceptionally(exception -> {
                            LOGGER.error("insertion operation failed with the following exception ",exception.getCause());
                            throw new CompletionException(exception);
                            //return null;
                        })
        ).toCompletableFuture().join();
        LOGGER.debug("record inserted succufully");

    }

    /**
     * Update a record in the databse
     * @param record a Java object to be updated into the databse
     */
    public void update(BaseModel record){
        LOGGER.debug("update a  record " + record);

        factory.withTransaction(
                (session, tx) -> session.merge(record)
                        .exceptionally(exception -> {
                            LOGGER.error("update operation failed with the following exception ",exception.getCause());
                            return null;
                        })
        ).toCompletableFuture().join();
        LOGGER.debug("record updated successfully");
    }

    /**
     * delete a record from the datbase
     * @param query
     */
    public void deleteByQuery(String query){
        LOGGER.debug("delete a  record with ");

        factory.withSession(
                session -> session.createQuery(query).executeUpdate()
                // wait for it to finish
        ).toCompletableFuture().join();
        LOGGER.debug("record deleted successfully");

    }

    public void close(){
        if(factory.isOpen()){
            factory.close();
        }
    }




}
