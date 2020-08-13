<template>
  <v-main>
    <BookmarkHeader />
    <router-view class="pl-82" />
  </v-main>
</template>

<script>
import BookmarkHeader from '@/views/header/BookmarkHeader.vue';

export default {
  name: 'MainPage',
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
