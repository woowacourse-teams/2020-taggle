<template>
  <div class="icon-btn">
    <v-btn v-if="hasBookmark" small icon @click="deleteBookmark"><v-icon>delete</v-icon></v-btn>
    <v-btn v-else small icon @click="addBookmark"><v-icon>get_app</v-icon></v-btn>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import { DELETE_BOOKMARK, CREATE_BOOKMARK } from '../store/share/actionsType.js';
import { BOOKMARK_ID } from '../store/share/gettersType.js';

export default {
  name: 'Buttons',
  props: {
    hasBookmark: {
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
    ...mapGetters([BOOKMARK_ID]),
  },
  methods: {
    ...mapActions([DELETE_BOOKMARK, CREATE_BOOKMARK]),
    async deleteBookmark() {
      await this[DELETE_BOOKMARK](this[BOOKMARK_ID]);
      this.$emit('deleteBookmark');
    },
    async addBookmark() {
      await this[CREATE_BOOKMARK](this[BOOKMARK_ID]);
      this.$emit('createBookmark');
    },
  },
};
</script>

<style>
.icon-btn {
  float: right;
}
</style>
