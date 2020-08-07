<template>
  <v-app>
    <BookmarkHeader />
    <v-main style="padding-left: 330px;">
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
