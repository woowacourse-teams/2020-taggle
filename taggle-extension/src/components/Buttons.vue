<template>
  <div class="icon-btn">
    <v-btn v-if="isNotDeletedBookmark" small icon @click="deleteBookmark"><v-icon>delete</v-icon></v-btn>
    <v-btn v-else small icon @click="addBookmark"><v-icon>get_app</v-icon></v-btn>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import { DELETE_BOOKMARK, CREATE_BOOKMARK } from '../store/share/actionsType.js';

export default {
  name: 'Buttons',
  props: {
    isNotDeletedBookmark: {
      type: Boolean,
      require: true,
    },
    bookmarkUrl: {
      type: String,
      require: true,
    },
  },
  data() {
    return {
      bookmark: {
        url: this.bookmarkUrl,
      },
    };
  },
  computed: {
    ...mapGetters(['bookmarkId']),
  },
  methods: {
    ...mapActions([DELETE_BOOKMARK, CREATE_BOOKMARK]),
    async deleteBookmark() {
      await this.removeBookmark();
      this.$emit('toggleDeleteBookmark');
    },
    async addBookmark() {
      await this.fetchOrCreateBookmark(this.bookmark);
      this.$emit('toggleDeleteBookmark');
    },
  },
};
</script>

<style>
.icon-btn {
  float: right;
}
</style>
