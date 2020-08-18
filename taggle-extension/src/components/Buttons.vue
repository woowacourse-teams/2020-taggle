<template>
  <div>
    <div class="icon-btn">
      <v-btn v-if="hasBookmark" small icon @click="onDeleteBookmark"><v-icon>delete</v-icon></v-btn>
      <v-btn v-else small icon @click="onCreateBookmark"><v-icon>get_app</v-icon></v-btn>
    </div>
  </div>
</template>

<script>
import { mapMutations, mapActions, mapGetters } from 'vuex';
import { DELETE_BOOKMARK, CREATE_BOOKMARK, FETCH_BOOKMARK_WITH_TAGS } from '@/store/share/actionsType.js';
import { BOOKMARK_ID } from '@/store/share/gettersType.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationsType.js';
import { SNACKBAR_MESSAGES } from '@/utils/constants.js';

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
      bookmarkCreateRequest: {
        url: this.bookmarkUrl,
      },
    };
  },
  computed: {
    ...mapGetters([BOOKMARK_ID]),
  },
  async created() {
    await this.onCreateBookmark();
  },
  methods: {
    ...mapMutations([SHOW_SNACKBAR]),
    ...mapActions([DELETE_BOOKMARK, CREATE_BOOKMARK, FETCH_BOOKMARK_WITH_TAGS]),
    async onCreateBookmark() {
      try {
        const createdBookmarkId = await this[CREATE_BOOKMARK](this.bookmarkCreateRequest);
        await this[FETCH_BOOKMARK_WITH_TAGS](createdBookmarkId);
        this.$emit('createBookmark');
      } catch (e) {
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.BOOKMARK.ADD.FAIL);
      }
    },
    async onDeleteBookmark() {
      try {
        await this[DELETE_BOOKMARK](this[BOOKMARK_ID]);
        this.$emit('deleteBookmark');
      } catch (e) {
        this[SHOW_SNACKBAR](SNACKBAR_MESSAGES.BOOKMARK.DELETE.FAIL);
      }
    },
  },
};
</script>

<style>
.icon-btn {
  float: right;
}
</style>
