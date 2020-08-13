<template>
  <v-app>
    <v-main v-if="isAuthenticated">
      <BookmarkHeader />
      <router-view class="pl-82" />
    </v-main>
    <v-main v-else>
      <LoginPage />
    </v-main>
  </v-app>
</template>

<script>
import BookmarkHeader from '@/views/header/BookmarkHeader.vue';
import LoginPage from '@/views/auth/LoginPage.vue';
import { mapGetters } from 'vuex';

export default {
  name: 'App',
  components: {
    LoginPage,
    BookmarkHeader,
  },
  computed: {
    ...mapGetters(['isAuthenticated']),
  },
};
</script>

<style>
.pl-82 {
  padding-left: 328px !important;
}
</style>
