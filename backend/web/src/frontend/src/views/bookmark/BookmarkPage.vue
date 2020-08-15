<template>
  <BookmarkCard />
</template>

<script>
import BookmarkCard from '@/views/bookmark/components/BookmarkCard.vue';
import { mapActions } from 'vuex';
import { FETCH_TAG_BOOKMARKS } from '@/store/share/actionTypes.js';

export default {
  name: 'BookmarkPage',
  beforeRouteUpdate(to, from, next) {
    next();
    this[FETCH_TAG_BOOKMARKS]({ tagId: this.$route.params.id });
  },
  created() {
    this[FETCH_TAG_BOOKMARKS]({ tagId: this.$route.params.id });
  },
  methods: {
    ...mapActions([FETCH_TAG_BOOKMARKS]),
  },
  components: {
    BookmarkCard,
  },
};
</script>
