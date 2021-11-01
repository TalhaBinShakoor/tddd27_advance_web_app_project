import axios from "axios";
/*
Api-client class which call/consumes Rest-api webservices needed by the Front-end
*/
export default class ApiService {
  //FIXME url should be in config file
  getAllWebServices() {
    return axios
      .get("http://89.47.166.22/:8080/api/getAllWebServices")
      .then((res) => res.data);
  }

  createWebServices(name, url, method, userData) {
    console.log("userData " + userData)
    return axios
      .post("http://89.47.166.22:8080/api/addWebService", {
        name: name,
        url: url,
        method: method,
      },
        {
          headers: { "Authorization": userData }
        })
      .then((res) => res.data);

  }

  updateWebService(id, name, url, method, userData) {
    return axios
      .post("http://89.47.166.22:8080/api/updateWebService", {
        id: id,
        name: name,
        url: url,
        method: method
      },
        {
          headers: { "Authorization": userData }
        })
      .then((res) => res.data)
      .catch((error) => {
        console.log("error " + error);
        if (error.response.status === 505) {
          throw new Error('Error occurred while updating the web service ');
        }
        throw error;
      });

  }

  deleteWebService(id, name, url, method, userData) {
    console.log("user data " + name + " " + userData);
    return axios
      .post("http://89.47.166.22:8080/api/deleteWebService", {
        id: id,
        name: name,
        url: url,
        method: method
      },
        {
          headers: { "Authorization": userData }
        })
      .then((res) => res.data)
      .catch((error) => {
        console.log("error " + error);
        if (error.response.status === 505) {
          throw new Error('Error occurred while deleting the web service ');
        }
        throw error;
      });

  }

  createUser(email, firstName, lastName, password) {
    console.log("Email", email);
    console.log("FirstName", firstName);
    console.log("LastName", lastName);
    console.log("Password", password);
    return axios
      .post("http://89.47.166.22:8080/api/addUser", {
        email: email,
        firstName: firstName,
        lastName: lastName,
        password: password
      })
      .then((res) => res.data);

  }

  updateUser(id, name, url, method) {
    return axios
      .post("http://89.47.166.22:8080/api/updateWebService", {
        id: id,
        name: name,
        url: url,
        method: method
      })
      .then((res) => res.data);

  }

  getApiStatus(userData) {
    console.log("userData " + userData);
    return axios
      .get("http://89.47.166.22:8080/api/getApiStatus", {
        headers: { "Authorization": userData }
      })
      .then((res) => res.data)
      .catch((error) => {
        console.log("error " + error);
        if (error.response.status === 500) {
          throw new Error('Error while trying to get API status  ');
        }
        throw error;
      });
  }

  authenticateUser(email, password) {
    return axios
      .post("http://89.47.166.22:8080/api/authenticate", {
        email: email,
        password: password
      })
      .then((res) => res.data)
      .catch((error) => {
        console.log("error " + error);
        if (error.response.status === 500) {
          throw new Error('Error while trying to login to the system ');
        }
        throw error;
      });

  }

  getWebServices(userData) {
    return axios
      .get("http://89.47.166.22:8080/api/getAllWebServices", {
        headers: { "Authorization": userData }
      })
      .then((res) => res.data)
      .catch((error) => {
        console.log("error " + error);
        if (error.response.status === 500) {
          throw new Error('Error while trying to get user\'s webservices  ');
        }
        throw error;
      });
  }

}
