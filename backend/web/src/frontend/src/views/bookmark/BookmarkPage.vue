<template>
  <BookmarkCard />
</template>

<script>
import BookmarkCard from '@/views/bookmark/components/BookmarkCard.vue';
import { mapActions } from 'vuex';
import { FETCH_BOOKMARKS } from '@/store/share/actionTypes.js';

export default {
  name: 'BookmarkPage',
  beforeRouteUpdate(to, from, next) {
    next();
    this[FETCH_BOOKMARKS]({ tagId: this.$route.params.id });
  },
  methods: {
    ...mapActions([FETCH_BOOKMARKS]),
  },
  components: {
    BookmarkCard,
  },
};
</script>
