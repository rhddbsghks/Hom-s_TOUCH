<template>
  <div>
    <div
      class="scrollbar b-table-sticky-header table-responsive"
      style="overflow-y: auto; overflow-x: auto; max-height: 500px"
    >
      <table class="table" style="width: 100% height:100%">
        <thead>
          <tr>
            <th width="220px">이름</th>
            <th width="220px">아이디</th>
            <th width="220px">비밀번호</th>
            <th width="300px">이메일</th>
            <th width="110px"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in userList" :key="user.id" style="height: 61px">
            <td>
              {{ user.name }}
            </td>
            <td>
              {{ user.id }}
            </td>
            <td>
              {{ user.pw }}
            </td>
            <td>
              {{ user.email }}
            </td>
            <td style="width: 60px">
              <b-button
                v-if="user.id != 'admin'"
                block
                id="deleteBtn"
                class="ml-2"
                @click="deleteUser(user)"
              >
                삭제
              </b-button>
            </td>
          </tr>
        </tbody>
      </table>

      <div style="position: absolute; width: 33%; left: 35.1%; bottom: 15%">
        <pagination style="margin: 0px" />
      </div>
    </div>
  </div>
</template>

<script>
import http from "@/util/http-common.js";
import Pagination from "./Pagination.vue";

export default {
  components: { Pagination },
  data() {
    return {
      userList: [],
      pageLimit: 6,
      pageOffset: 0,
    };
  },
  created() {
    this.loadUser();
  },
  watch: {
    "$route.params": function () {
      this.loadUser();
    },
  },

  methods: {
    deleteUser(user) {
      if (!confirm(`${user.name} 회원을 정말로 탈퇴시키겠습니까?`)) return;
      http.delete(`/api/user/${user.id}`).then(() => {
        this.loadUser();
      });
    },
    loadUser() {
      this.pageOffset = this.$route.params.page;
      http
        .get("/api/user/list", {
          params: { pg: this.pageOffset, spp: this.pageLimit },
        })
        .then(({ data }) => {
          this.userList = data;
        });
    },
  },
};
</script>

<style lang="scss" scoped>
thead {
  th {
    position: sticky;
    top: 0;
    z-index: 2;
    background: #d4d6ff; /* fallback for old browsers */
  }
}
tr {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: none;
}

#deleteBtn {
  border: 0px;
  background-image: linear-gradient(135deg, #f05f57 10%, #360940 100%);
  border-color: rgba(255, 255, 255, 0);
}
</style>
