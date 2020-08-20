<template>
  <v-app id="inspire">
    <BookmarkHeader />
    <v-main>
      <v-container>
        <router-view></router-view>
      </v-container>
      <Snackbar />
    </v-main>
  </v-app>
</template>

<script>
import BookmarkHeader from '@/views/header/BookmarkHeader.vue';
import Snackbar from '@/views/common/component/Snackbar.vue';

export default {
  name: 'MainPage',
  components: {
    Snackbar,
    BookmarkHeader,
  },
};
</script>
