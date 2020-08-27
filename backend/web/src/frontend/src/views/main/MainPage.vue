<template>
  <v-app id="inspire">
    <BookmarkHeader />
    <v-main>
      <v-container style="height: 100% !important;">
        <router-view></router-view>
      </v-container>
      <BookmarkAddModal v-if="bookmarkAddModalEnabled()" />
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
  methods: {
    bookmarkAddModalEnabled() {
      return this.$router.history.current.path !== '/profile';
    },
  },
};
</script>
