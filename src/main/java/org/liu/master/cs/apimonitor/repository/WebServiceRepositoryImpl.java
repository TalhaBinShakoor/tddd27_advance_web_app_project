package org.liu.master.cs.apimonitor.repository;


import org.liu.master.cs.apimonitor.domain.BaseModel;
import org.liu.master.cs.apimonitor.domain.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Mutaz Hussein Kabashi
 * @version 1.0
 */
public class WebServiceRepositoryImpl extends  DataBaseRepository<WebService> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceRepositoryImpl.class);

    /**
     *
     * @param persistenceUnitName : DataBase profile/Connection
     */
    public WebServiceRepositoryImpl(String persistenceUnitName) {
        super();
        super.init(persistenceUnitName);
    }

    @Override
    public String getFindAllQuery() {

        return "SELECT w FROM WebService w";
    }

    @Override
    public Class getModelType() {
       return WebService.class;
    }

    public List<WebService> findByWebServicesByUser(Integer userId){
        LOGGER.debug("userId "+userId);
        List<WebService> result =  super.findByQueryAndParameter("SELECT w FROM WebService w join fetch w.user where w.user.id=:userId","userId",userId);
        return result;
    }

    public void deleteWebServicesById(Integer webserviceId){
        LOGGER.debug("webServiceId "+webserviceId);
         super.deleteByQuery("delete from WebService w where w.id="+ webserviceId);

    }
}
