<template>
  <v-app id="inspire">
    <BookmarkHeader />
    <v-main>
      <v-container>
        <router-view></router-view>
      </v-container>
      <BookmarkAddModal />
      <Snackbar />
    </v-main>
  </v-app>
</template>

<script>
import BookmarkHeader from '@/views/header/BookmarkHeader.vue';
import BookmarkAddModal from '@/views/main/component/BookmarkAddModal.vue';
import Snackbar from '@/views/main/component/Snackbar.vue';

export default {
  name: 'MainPage',
  components: {
    BookmarkHeader,
    BookmarkAddModal,
    Snackbar,
  },
};
</script>
