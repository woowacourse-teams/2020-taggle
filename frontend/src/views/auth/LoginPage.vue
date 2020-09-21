<template>
  <v-main>
    <v-container class="login-container">
      <v-row justify="center" align-content="center">
        <v-col class="text-h1 text-right font-weight-medium text--secondary login-title pa-4">
          Bookmarking,<br />
          Tagging,<br />
          Subscribing
        </v-col>
        <v-col justify="center" align-self="center" class="text-center pa-4">
          <v-row justify="center">
            <v-img src="@/assets/images/login/bookmark.png" class="login-decoration-image"></v-img>
            <v-img src="@/assets/images/login/tag.png" class="login-decoration-image"></v-img>
            <v-img src="@/assets/images/login/subscribe.png" class="login-decoration-image"></v-img>
          </v-row>
          <div class="ma-2">
            <v-btn class="login-button" large rounded depressed href="/oauth2/authorization/google">
              <v-img class="login-image" src="@/assets/images/login/google-button.png" left></v-img>
              Sign In With Google
            </v-btn>
          </div>
          <div class="ma-2">
            <v-btn class="login-button yellow accent-4" large rounded depressed href="/oauth2/authorization/kakao">
              <v-img class="login-image" src="@/assets/images/login/kakao-button.png" left></v-img>
              Sign In With Kakao
            </v-btn>
          </div>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
export default {
  name: 'LoginPage',
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
  max-height: 150px !important;
  max-width: 150px !important;
  margin: 25px !important;
}

.login-image {
  max-height: 16px !important;
  max-width: 16px !important;
  margin-right: 8px !important;
}

.login-button {
  min-width: 520px !important;
  margin-top: 4px !important;
}
</style>
