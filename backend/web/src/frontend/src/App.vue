<template>
  <v-app>
    <BookmarkHeader />
    <v-content style="padding-left: 330px;">
      <router-view />
    </v-content>
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
