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
    alert('한번 렌더링이 완료된 후 id만 업데이트 했다');
    this[FETCH_TAG_BOOKMARKS]({ tagId: this.$route.params.id });
  },
  mounted() {
    alert('처음에 mount 됐다');
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
