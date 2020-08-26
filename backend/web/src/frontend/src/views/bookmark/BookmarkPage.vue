<template>
  <div>
    <BookmarkCard />
    <Infinite-loading
      v-if="bookmarks.length"
      :identifier="infiniteId"
      @infinite="infiniteHandler"
      force-use-infinite-wrapper="BookmarkCard"
      spinner="waveDots"
    >
      <div slot="no-more" class="text--secondary">북마크를 모두 불러왔습니다.</div>
    </Infinite-loading>
  </div>
</template>

<script>
import BookmarkCard from '@/views/bookmark/components/BookmarkCard.vue';
import InfiniteLoading from 'vue-infinite-loading';
import { mapActions, mapGetters } from 'vuex';
import { CLEAR_BOOKMARKS, FETCH_MORE_BOOKMARKS, FETCH_TAG_WITH_BOOKMARKS } from '@/store/share/actionTypes.js';
import { BOOKMARKS } from '@/store/share/getterTypes.js';

export default {
  name: 'BookmarkPage',
  data() {
    return {
      page: 1,
      display: 10,
      infiniteId: +new Date(),
    };
  },
  beforeRouteUpdate(to, from, next) {
    this.clearBookmarks();
    next();
    this.changeTag();
  },
  created() {
    this.changeTag();
  },
  computed: {
    ...mapGetters([BOOKMARKS]),
  },
  methods: {
    ...mapActions([FETCH_TAG_WITH_BOOKMARKS, FETCH_MORE_BOOKMARKS, CLEAR_BOOKMARKS]),
    async infiniteHandler($state) {
      const bookmarks = await this.fetchMoreBookmarks({
        tagId: this.$route.params.id,
        start: this.page,
        display: this.display,
      });
      if (bookmarks.length) {
        this.page += 1;
        $state.loaded();
      } else {
        $state.complete();
      }
    },
    async changeTag() {
      this.page = 1;
      this.infiniteId += 1;
      const bookmarks = await this.fetchTagWithBookmarks({
        tagId: this.$route.params.id,
        start: this.page,
        display: this.display,
      });
      if (bookmarks.length) {
        this.page += 1;
      }
    },
  },
  components: {
    BookmarkCard,
    InfiniteLoading,
  },
};
</script>
