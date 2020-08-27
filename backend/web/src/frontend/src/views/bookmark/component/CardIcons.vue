<template>
  <v-card-actions>
    <v-spacer></v-spacer>
    <v-btn icon>
      <v-icon>mdi-heart</v-icon>
    </v-btn>
    <v-btn icon>
      <v-icon>mdi-share-variant</v-icon>
    </v-btn>
    <TagEditModal :bookmark="bookmark" />
    <v-btn icon @click.prevent="onDeleteBookmark">
      <v-icon>mdi-delete</v-icon>
    </v-btn>
    <ConfirmDialog ref="confirm" />
  </v-card-actions>
</template>

<script>
import { DELETE_BOOKMARK, FETCH_CATEGORIES, FETCH_TAG_WITH_BOOKMARKS } from '@/store/share/actionTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import { mapActions, mapGetters, mapMutations } from 'vuex';
import { TAG_ID } from '@/store/share/getterTypes.js';
import TagEditModal from '@/views/bookmark/component/TagEditModal.vue';
import ConfirmDialog from '@/views/common/component/ConfirmDialog.vue';

export default {
  name: 'CardIcons',
  components: {
    TagEditModal,
    ConfirmDialog,
  },
  props: {
    bookmark: {
      type: Object,
      required: true,
    },
  },
  computed: {
    ...mapGetters([TAG_ID]),
  },
  methods: {
    ...mapActions([DELETE_BOOKMARK, FETCH_CATEGORIES, FETCH_TAG_WITH_BOOKMARKS]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onDeleteBookmark() {
      const confirm = await this.$refs.confirm.open('북마크 삭제', '정말로 해당 북마크를 삭제하시겠습니까?');
      if (!confirm) {
        return;
      }
      try {
        await this[DELETE_BOOKMARK]({ bookmarkId: this.bookmark.id });
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.DELETE.SUCCESS);
      } catch {
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.DELETE.FAIL);
      }
    },
  },
};
</script>
