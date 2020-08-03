<template>
  <div class="icon-btn">
    <v-btn v-if="isNotDeletedBookmark" small icon @click="deleteBookmark"><v-icon>delete</v-icon></v-btn>
    <v-btn v-else small icon @click="addBookmark"><v-icon>get_app</v-icon></v-btn>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { DELETE_BOOKMARK, FETCH_OR_CREATE_BOOKMARK } from '../store/share/actionsType.js';

export default {
  name: 'Buttons',
  computed: {
    ...mapGetters(['bookmarkId']),
  },
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
  methods: {
    async deleteBookmark() {
      await this.$store.dispatch(DELETE_BOOKMARK);
      this.$emit('toggleDeleteBookmark');
    },
    async addBookmark() {
      await this.$store.dispatch(FETCH_OR_CREATE_BOOKMARK, this.bookmark);
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
