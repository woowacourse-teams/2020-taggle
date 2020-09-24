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
    <UserGuideDialog :userGuideDialog="guideDialog" :permanentClose="true" @close="guideDialog = false" />
  </v-app>
</template>

<script>
import BookmarkHeader from '@/views/header/BookmarkHeader.vue';
import BookmarkAddModal from '@/views/main/component/BookmarkAddModal.vue';
import UserGuideDialog from '@/views/header/component/UserGuideDialog.vue';
import Snackbar from '@/views/main/component/Snackbar.vue';

export default {
  name: 'MainPage',
  components: {
    BookmarkHeader,
    BookmarkAddModal,
    UserGuideDialog,
    Snackbar,
  },
  data() {
    return {
      guideDialog: false,
    };
  },
  created() {
    this.guideDialog = localStorage.getItem('dont_look_again') !== 'true';
  },
  methods: {
    bookmarkAddModalEnabled() {
      return this.$router.history.current.path !== '/profile';
    },
  },
};
</script>
