<template>
  <v-app>
    <BookmarkHeader />
    <v-main class="pl-82">
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import BookmarkHeader from '@/views/header/BookmarkHeader.vue';

export default {
  name: 'App',

  components: {
    BookmarkHeader,
  },
};
</script>

<style>
.pl-82 {
  padding-left: 328px !important;
}
</style>
