import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api
    .post(`/api/user/login`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/api/user/info/${userid}`).then(success).catch(fail);
}

// function logout(success, fail)

export { login, findById };
