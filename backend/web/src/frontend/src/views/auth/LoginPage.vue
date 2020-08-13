<template>
  <v-container class="login-container">
    <v-row justify="center" align-content="center">
      <v-col class="text-h1 text-right font-weight-medium text--secondary login-title pa-4">
        Bookmarking,<br />
        Tagging,<br />
        Subscribing
      </v-col>
      <v-col justify="center" align-self="center" class="text-center pa-4">
        <v-row justify="center">
          <v-img src="@/assets/images/bookmark.png" class="login-decoration-image"></v-img>
          <v-img src="@/assets/images/tag.png" class="login-decoration-image"></v-img>
          <v-img src="@/assets/images/subscribe.png" class="login-decoration-image"></v-img>
        </v-row>
        <div class="ma-2">
          <v-btn class="login-button" large rounded depressed @click.prevent="login">
            <v-img class="login-image" src="@/assets/images/google-button.png" left></v-img>
            Sign In With Google
          </v-btn>
        </div>
        <div class="ma-2">
          <v-btn class="login-button yellow accent-4" large rounded depressed @click.prevent="login">
            <v-img class="login-image" src="@/assets/images/kakao-button.png" left></v-img>
            Sign In With Kakao
          </v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { LOGIN } from '@/store/share/actionTypes.js';
import { mapActions } from 'vuex';

export default {
  name: 'LoginPage',
  methods: {
    ...mapActions({ login: LOGIN }),
  },
};
</script>

<style>
.login-container {
  display: flex;
  height: 100%;
  align-items: center;
  justify-content: center;
}

.login-title {
  line-height: 7rem !important;
}

.login-decoration-image {
  max-height: 150px;
  max-width: 150px;
  margin: 25px;
}

.login-image {
  max-height: 16px;
  max-width: 16px;
  margin-right: 8px;
}

.login-button {
  min-width: 520px !important;
  margin-top: 4px;
}
</style>
