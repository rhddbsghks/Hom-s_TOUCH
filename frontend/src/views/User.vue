<template>
  <header
    class="py-5 bg-light mb-4"
    style="height: 90vh; padding: 0px !important"
  >
    <div class="container col-lg-5 col-lg-7" style="height: 90%">
      <div class="card mb-4" style="margin-top: 10%; height: 85% !important">
        <div class="card-body mt-4">
          <h2 class="card-title" v-show="!edit">회원 정보 확인</h2>
          <h2 class="card-title" v-show="edit">회원 정보 수정</h2>
          <!-- <p class="card-text"></p> -->
          <div class="form-block">
            <div class="uploader-wrapper">
              <div class="image-dropzone">
                <p>프로필 사진을 등록해보세요.</p>
              </div>
              <!-- <input
                type="file"
                style="display: none"
                ref="imageInput"
                accept="image/png,image/jpeg,image/jpg"
                @change="handleImageChoosen"
              /> -->
            </div>
            <form class="form-horizontal">
              <div class="form-group mt-3">
                <label for="id">아이디: </label>
                <input
                  readonly
                  class="form-control"
                  id="updateid"
                  placeholder="Enter ID"
                  v-model="id"
                />
              </div>

              <div class="form-group mt-2" v-show="edit">
                <label for="pw">비밀번호: </label>
                <input
                  type="password"
                  class="form-control edditable"
                  id="updatepw"
                  v-model="pw"
                />
              </div>
              <div class="form-group mt-2" v-show="!edit">
                <label for="name">이름: </label>
                <input
                  readonly
                  type="text"
                  class="form-control edditable"
                  id="updatename"
                  v-model="name"
                />
              </div>
              <div class="form-group mt-2" v-show="edit">
                <label for="name">이름: </label>
                <input
                  type="text"
                  class="form-control edditable"
                  id="updatename"
                  v-model="name"
                />
              </div>
              <div class="form-group mt-2" v-show="!edit">
                <label for="email">이메일: </label>
                <input
                  readonly
                  type="text"
                  class="form-control edditable"
                  id="updateemail"
                  v-model="email"
                />
              </div>
              <div class="form-group mt-2" v-show="edit">
                <label for="email">이메일: </label>
                <input
                  type="text"
                  class="form-control edditable"
                  id="updateemail"
                  v-model="email"
                />
              </div>
            </form>
          </div>

          <div
            class="formBtn"
            style="
              float: right;
              margin-right: 150px;
              width: 18%;
              display: flex;
              justify-content: space-between;
            "
          >
            <b-button
              class="btn btn-group btn-animated mt-3"
              id="searchBtn"
              @click="edit = !edit"
              v-show="!edit"
            >
              <i class="bi-pencil-square"></i>수정
            </b-button>
            <b-button
              class="btn btn-group btn-animated mt-3"
              id="searchBtn"
              @click="modify"
              v-show="edit"
            >
              <i class="bi-pencil-square"></i>확인
            </b-button>

            <!-- Button trigger modal -->
            <b-button
              class="btn btn-danger btn-group btn-animated mt-3"
              @click="$bvModal.show('deleteCheckModal')"
              id="deleteBtn"
            >
              <i class="bi-trash"></i>회원 탈퇴
            </b-button>
          </div>

          <!-- Modal -->

          <b-modal id="deleteCheckModal" hide-footer title="회원 정보 삭제">
            회원님의 모든 정보가 삭제됩니다. 진행하시겠습니까?
            <div id="confirmBtn">
              <b-button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
                style="margin-right: 10px"
                @click="$bvModal.hide('deleteCheckModal')"
              >
                아니오.
              </b-button>
              <button
                type="button"
                class="btn btn-danger"
                id="deleteBtn"
                data-bs-dismiss="modal"
                @click="deleteUser"
              >
                네. 삭제합니다.
              </button>
            </div>
          </b-modal>
          <!-- </b-modal> -->

          <div
            class="btn btn-group btn-success btn-animated mt-3 float-end"
            style="display: none"
            id="checkBtn"
          >
            확인 <i class="bi-check"></i>
          </div>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
import { mapState, mapActions } from "vuex";
import http from "@/util/http-common.js";
const memberStore = "memberStore";
export default {
  name: "MyPage",
  data() {
    return {
      id: "",
      pw: "",
      name: "",
      email: "",
      edit: false,
    };
  },
  created() {
    this.id = this.userInfo.id;
    this.name = this.userInfo.name;
    this.email = this.userInfo.email;
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["logout", "getUserInfo"]),
    deleteUser() {
      http.delete(`/api/user/${this.id}`).then(() => {
        this.logout();
        this.$router.push({ name: "Home" });
      });
    },
    modify() {
      if (this.pw == null || this.pw == "") {
        alert("비밀번호를 입력하세요.");
        return;
      }

      if (this.name == null || this.name == "") {
        alert("이름을 입력하세요.");
        return;
      }
      http
        .put(`/api/user/${this.id}`, {
          pw: this.pw,
          name: this.name,
          email: this.email,
        })
        .then(() => {
          let token = sessionStorage.getItem("access-token");
          this.getUserInfo(token);

          this.edit = !this.edit;
          console.log("수정완료");
        });
    },
  },
};
</script>

<style lang="scss" scoped>
.card-title {
  font-size: 2.4em;
  padding-bottom: 40px;
}
label {
  font-size: 1.5em;
}
.form-group {
  display: flex;
  justify-content: space-between;
  margin-bottom: 40px;
}
.form-control {
  width: 60%;
}
.form-block {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.form-horizontal {
  width: 450px;
  margin-right: 120px;
}

.uploader-wrapper {
  margin-top: 10px;
  margin-left: 20px;
  margin-right: 60px;
}

.image-dropzone {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 320px;
  height: 240px;
  margin: 0 auto;
  border: 2px dashed #ccc;
  cursor: pointer;
  border-radius: 30px;
}

#confirmBtn {
  display: flex;
  margin-top: 30px;
  justify-content: right;
}

.container {
  display: flex;
  justify-content: center;
}
</style>
