package org.liu.master.cs.apimonitor.repository;


import org.liu.master.cs.apimonitor.domain.User;
import org.liu.master.cs.apimonitor.domain.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Talha Bin Shakoor
 * @version 1.0
 */
public class UserRepositoryImpl extends  DataBaseRepository<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryImpl.class);

    /**
     *
     * @param persistenceUnitName : DataBase profile/Connection
     */
    public UserRepositoryImpl(String persistenceUnitName) {
        super();
        super.init(persistenceUnitName);
    }

    @Override
    public String getFindAllQuery() {

        return "SELECT u FROM User u";
    }

    @Override
    public Class getModelType() {
       return User.class;
    }

    public User findByEmail(String email){
       LOGGER.debug("email "+email);
       List<User> result =  super.findByQuery("SELECT u FROM User u where email='"+email+"'");
       if(result!=null && result.size()!=0){
           return result.get(0);
       }
       return null;
    }


}
