<template>
  <v-app>
    <BookmarkHeader />
    <router-view />
  </v-app>
</template>

<script>
import BookmarkHeader from '@/views/header/BookmarkHeader.vue';

export default {
  name: 'App',

  components: {
    BookmarkHeader
  }
};
</script>
