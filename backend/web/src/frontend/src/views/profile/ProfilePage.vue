<template>
  <section style="margin: auto; width: 75%">
    <div class="profile-img">
      <v-avatar size="200">
        <img :src="user.picture" alt="evan" />
      </v-avatar>
      <h2 style="padding-top: inherit">{{ user.nickName }}</h2>
    </div>
    <div class="profile">
      <div class="profile-title">
        <h2>이메일 주소</h2>
      </div>
      <div class="profile-content">
        {{ user.email }}
      </div>
    </div>
    <div class="profile">
      <div class="profile-title">
        <h2>알람 이메일 주소</h2>
      </div>
      <div class="profile-content">
        {{ user.notificationEmail }}
      </div>
      <div class="profile-button">
        <v-btn>수정</v-btn>
      </div>
    </div>
    <div class="profile">
      <div class="profile-title">
        <h2>이메일 알람 설정</h2>
      </div>
      <div class="profile-content">
        <v-switch v-model="isNotice" inset color="success"></v-switch>
      </div>
    </div>
    <div class="profile">
      <div class="profile-title">
        <h2>회원 탈퇴</h2>
      </div>
      <div class="profile-content">
        <v-btn depressed color="success" large @click="onDeleteUser"><h3>회원 탈퇴</h3></v-btn>
      </div>
    </div>
  </section>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex';
import { UPDATE_NOTIFICATION_EMAIL, UPDATE_NOTIFICATION_ENABLED, DELETE_USER } from '@/store/share/actionTypes.js';
import { USER } from '@/store/share/getterTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';

export default {
  name: 'ProfilePage',
  data() {
    return {
      isNotice: '',
      notificationEmail: '',
    };
  },
  created() {
    this.isNotice = this.user.notificationEnabled;
  },
  methods: {
    ...mapActions([UPDATE_NOTIFICATION_EMAIL, UPDATE_NOTIFICATION_ENABLED, DELETE_USER]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onUpdateNotificationEmail() {
      try {
        await this[UPDATE_NOTIFICATION_EMAIL]({ notificationEmail: this.notificationEmail });
        await this[SHOW_SNACKBAR](MESSAGES.USER.NOTIFICATION_EMAIL.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.USER.NOTIFICATION_EMAIL.FAIL);
      }
    },
    async onUpdateNotificationEnabled() {
      try {
        await this[UPDATE_NOTIFICATION_ENABLED]({ notificationEnabled: this.isNotice });
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.USER.NOTIFICATION_ENABLED.FAIL);
      }
    },
    async onDeleteUser() {
      try {
        await this[DELETE_USER]();
        await this[SHOW_SNACKBAR](MESSAGES.USER.DELETE.SUCCESS);
        await this.$router.replace('/signin');
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.USER.DELETE.FAIL);
      }
    },
  },
  computed: {
    ...mapGetters([USER]),
  },
  watch: {
    async isNotice() {
      await this.onUpdateNotificationEnabled();
    },
  },
};
</script>

<style>
.profile-img {
  text-align: center;
  padding-bottom: 1.5rem;
  padding-top: 1.5rem;
}

.profile {
  display: flex;
  padding-bottom: 1.5rem;
  padding-top: 1.5rem;
  border-bottom: 1px solid rgba(0, 0, 0, 0.12);
}

.profile-title {
  align-self: center;
  width: 15rem;
}

.profile-content {
  align-self: center;
  font-size: 1.1rem;
}
</style>
