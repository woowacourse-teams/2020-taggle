<template>
  <v-main>
    <BookmarkHeader />
    <router-view class="pl-82" />
    <Snackbar />
  </v-main>
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

<style>
.pl-82 {
  padding-left: 328px !important;
}
</style>
