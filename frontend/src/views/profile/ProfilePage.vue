<template>
  <section class="profile-section">
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
        <h2>알림 이메일 주소</h2>
      </div>
      <div v-if="fixEnabled" class="profile-content">
        <div class="profile-notification-email">
          <v-text-field full-width outlined hide-details clearable v-model="notificationEmail"></v-text-field>
        </div>
      </div>
      <div v-else class="profile-content">
        <div class="profile-notification-email">
          {{ notificationEmail }}
        </div>
      </div>
      <div v-if="fixEnabled" class="profile-button">
        <v-btn depressed color="success" large @click="onUpdateNotificationEmail"><h3>저장</h3></v-btn>
      </div>
      <div v-else class="profile-button">
        <v-btn depressed color="success" large @click="changeFixEnabled"><h3>수정</h3></v-btn>
      </div>
    </div>
    <div class="profile">
      <div class="profile-title">
        <h2>이메일 알림 설정</h2>
      </div>
      <div class="profile-content">
        <v-switch v-model="notificationEnabled" inset color="success"></v-switch>
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
import { DELETE_USER, UPDATE_PROFILE } from '@/store/share/actionTypes.js';
import { USER } from '@/store/share/getterTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';

export default {
  name: 'ProfilePage',
  data() {
    return {
      fixEnabled: false,
      notificationEnabled: '',
      notificationEmail: '',
    };
  },
  created() {
    this.notificationEnabled = this.user.notificationEnabled;
    this.notificationEmail = this.user.notificationEmail;
  },
  methods: {
    ...mapActions([UPDATE_PROFILE, DELETE_USER]),
    ...mapMutations([SHOW_SNACKBAR]),
    changeFixEnabled() {
      this.fixEnabled = true;
    },
    changeFixDisabled() {
      this.fixEnabled = false;
    },
    async onUpdateNotificationEmail() {
      try {
        await this[UPDATE_PROFILE]({ notificationEmail: this.notificationEmail });
        await this[SHOW_SNACKBAR](MESSAGES.USER.NOTIFICATION_EMAIL.SUCCESS);
        await this.changeFixDisabled();
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.USER.NOTIFICATION_EMAIL.FAIL);
      }
    },
    async onUpdateNotificationEnabled() {
      try {
        await this[UPDATE_PROFILE]({ notificationEnabled: this.notificationEnabled });
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
    async notificationEnabled() {
      await this.onUpdateNotificationEnabled();
    },
  },
};
</script>

<style>
.profile-section {
  margin: auto;
  width: 65%;
}

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
  display: flex;
  width: 56%;
  align-self: center;
  font-size: 1.1rem;
}

.profile-notification-email {
  width: 100%;
  align-self: center;
  margin-right: 1rem;
}

.profile-button {
  align-self: center;
  margin-left: auto;
}
</style>
