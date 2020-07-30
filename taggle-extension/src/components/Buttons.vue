<template>
  <div class="icon-btn">
    <v-btn small icon @click="deleteBookmark"><v-icon>delete</v-icon></v-btn>
  </div>
</template>

<script>
import BookmarkService from '../api/module/bookmark.js';

export default {
  name: 'Buttons',
  props: {
    bookmarkId: {
      type: String,
    },
  },
  data() {
    return {};
  },
  methods: {
    deleteBookmark: function() {
      BookmarkService.delete(this.bookmarkId);
    },
  },
};
</script>

<style>
.icon-btn {
  float: right;
}
</style>
