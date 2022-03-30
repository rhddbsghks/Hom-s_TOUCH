<template>
  <div class="home" style="text-align: center">
    <div id="title">
      <h1>Hom's TOUCH</h1>
      <h3 style="margin-top: 10%">당신의 손길이 깃든</h3>
      <h3 style="margin-top: 5%">나만의 보금자리</h3>
    </div>

    <div class="login-box">
      <div id="loginbox" v-show="user.show">
        <h2>Login</h2>
        <form onsubmit="return false">
          <div class="user-box">
            <input
              type="text"
              @keyup.enter="confirm"
              v-model="user.id"
              required=""
            />
            <label>ID</label>
          </div>
          <div class="user-box">
            <input
              type="password"
              @keyup.enter="confirm;"
              v-model="user.pw"
              required=""
            />
            <label>Password</label>
          </div>
          <button
            class="custom-btn btn-6"
            @click="
              user.show = !user.show;
              join.show = !join.show;
            "
          >
            <span>Sign Up</span>
          </button>

          <button class="custom-btn btn-6" @click="confirm">
            <span>Sign In</span>
          </button>
        </form>
      </div>

      <div id="joinbox" v-show="join.show">
        <h2>Welcome</h2>
        <form onsubmit="return false">
          <div class="user-box">
            <input
              type="text"
              @keyup.enter="signup"
              v-model="join.id"
              required=""
            />
            <label>ID</label>
          </div>
          <div class="user-box">
            <input
              type="password"
              @keyup.enter="signup"
              v-model="join.pw"
              required=""
            />
            <label>Password</label>
          </div>
          <div class="user-box">
            <input
              type="password"
              @keyup.enter="signup"
              required=""
              v-model="join.confirmpw"
            />
            <label>Confirm Password</label>
          </div>
          <div class="user-box">
            <input
              type="text"
              @keyup.enter="signup"
              v-model="join.name"
              required=""
            />
            <label>Name</label>
          </div>
          <div class="user-box">
            <input
              type="text"
              @keyup.enter="signup"
              v-model="join.email"
              required=""
            />
            <label>Email</label>
          </div>
          <button class="custom-btn btn-6" @click="signup">
            <span>Sign Up</span>
          </button>

          <button
            class="custom-btn btn-6"
            @click="
              user.show = !user.show;
              join.show = !join.show;
            "
          >
            <span>Sign In</span>
          </button>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import http from "@/util/http-common.js";
const memberStore = "memberStore";

export default {
  name: "Home",
  data() {
    return {
      user: {
        show: true,
        id: null,
        pw: null,
      },
      join: {
        show: false,
        id: null,
        pw: null,
        confirmpw: null,
        name: null,
        email: null,
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "Main" });
      } else {
        alert("일치하는 회원정보가 없습니다!");
        this.user.id = null;
        this.user.pw = null;
      }
    },

    signup() {
      if (!this.join.id) {
        alert("아이디를 확인하세요.");
        return;
      }
      if (!this.join.pw) {
        alert("비밀번호를 확인하세요.");
        return;
      }
      if (this.join.pw != this.join.confirmpw) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }
      if (!this.join.name) {
        alert("이름을 확인하세요.");
        return;
      }
      if (!this.join.email) {
        alert("이메일을 확인하세요.");
        return;
      }
      console.log(this.join.id);
      http
        .get("/api/user/idCheck", {
          params: {
            ckid: this.join.id,
          },
        })
        .then(({ data }) => {
          console.log(data);
          if (data == 1) {
            alert("이미 사용중인 아이디입니다.");
            return;
          }

          http
            .post("/api/user", {
              id: this.join.id,
              pw: this.join.pw,
              name: this.join.name,
              email: this.join.email,
            })
            .then(() => {
              alert("회원가입 완료");
              location.reload();
            });
        });
    },
  },
};
</script>

<style lang="scss" scoped>
#title {
  position: absolute;
  top: 30%;
  left: 13%;
}

h1 {
  margin: 0;
}

.home {
  margin: 0px;
  height: 100vh;
  background-image: url("../assets/homeback.jpg");
  background-size: cover;
}

/* 로그인창 */
.login-box {
  position: absolute;
  top: 50%;
  left: 72%;
  width: 500px;
  padding: 40px;
  transform: translate(-50%, -50%);

  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0, 0, 0, 0.3);
  border-radius: 10px;
}

.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: rgb(26, 79, 129);
  text-align: center;
}

.login-box .user-box {
  position: relative;
}

.login-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: rgb(92, 113, 133);
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}
.login-box .user-box label {
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: rgb(113, 139, 163);
  pointer-events: none;
  transition: 0.5s;
}

.login-box .user-box input:focus ~ label,
.login-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: rgb(113, 139, 163);
  font-size: 12px;
}

@keyframes btn-anim4 {
  0% {
    bottom: -100%;
  }
  50%,
  100% {
    bottom: 100%;
  }
}

/* 로그인버튼 */
button {
  margin: 20px;
}
.custom-btn {
  width: 130px;
  height: 40px;
  color: #fff;
  border-radius: 5px;
  padding: 10px 25px;
  font-family: "Lato", sans-serif;
  font-weight: 500;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  display: inline-block;
  box-shadow: inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5),
    7px 7px 20px 0px rgba(0, 0, 0, 0.1), 4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  outline: none;
}
.btn-6 {
  background: rgb(247, 150, 192);
  background: #7a7ad8;
  line-height: 42px;
  padding: 0;
  border: none;
}
.btn-6 span {
  position: relative;
  display: block;
  width: 100%;
  height: 100%;
  font-size: 1.5em;
}
.btn-6:before,
.btn-6:after {
  position: absolute;
  content: "";
  height: 0%;
  width: 1px;
  box-shadow: -1px -1px 20px 0px rgba(255, 255, 255, 1),
    -4px -4px 5px 0px rgba(255, 255, 255, 1),
    7px 7px 20px 0px rgba(0, 0, 0, 0.4), 4px 4px 5px 0px rgba(0, 0, 0, 0.3);
}
.btn-6:before {
  right: 0;
  top: 0;
  transition: all 500ms ease;
}
.btn-6:after {
  left: 0;
  bottom: 0;
  transition: all 500ms ease;
}
.btn-6:hover {
  background: #afafd3;
  color: #7a7ad8;
  box-shadow: none;
}
.btn-6:hover:before {
  transition: all 500ms ease;
  height: 100%;
}
.btn-6:hover:after {
  transition: all 500ms ease;
  height: 100%;
}
.btn-6 span:before,
.btn-6 span:after {
  position: absolute;
  content: "";
  box-shadow: -1px -1px 20px 0px rgba(255, 255, 255, 1),
    -4px -4px 5px 0px rgba(255, 255, 255, 1),
    7px 7px 20px 0px rgba(0, 0, 0, 0.4), 4px 4px 5px 0px rgba(0, 0, 0, 0.3);
}
.btn-6 span:before {
  left: 0;
  top: 0;
  width: 0%;
  height: 0.5px;
  transition: all 500ms ease;
}
.btn-6 span:after {
  right: 0;
  bottom: 0;
  width: 0%;
  height: 0.5px;
  transition: all 500ms ease;
}
.btn-6 span:hover:before {
  width: 100%;
}
.btn-6 span:hover:after {
  width: 100%;
}

.login-box {
  h2 {
    font-size: 3rem;
  }
}
//title
@import url(https://fonts.googleapis.com/css?family=Righteous);
h1 {
  display: inline-block;
  color: white;
  font-family: "Righteous", serif;
  font-size: 7rem;
  text-shadow: 0.03em 0.03em 0 hsla(230, 40%, 50%, 1);
}
h3 {
  font-size: 2rem;
  color: rgb(251, 254, 255);
  text-shadow: 0.03em 0.03em 0 hsla(230, 40%, 50%, 1);
}
</style>
