<template>
  <v-row
    v-if="isInitialLoadingCompleted && isBookmarksEmpty"
    justify="center"
    align="center"
    style="height: 100% !important;"
  >
    <v-col>
      <div class="ma-3 text-center">
        <h3 class="text--primary">북마크가 존재하지 않습니다.</h3>
        <span class="text--secondary">북마크에 이 태그를 달아보세요 :)</span>
      </div>
    </v-col>
  </v-row>
  <div v-else>
    <BookmarkCard />
    <Infinite-loading
      :identifier="infiniteId"
      @infinite="infiniteHandler"
      force-use-infinite-wrapper="BookmarkCard"
      spinner="waveDots"
    >
      <div slot="no-more" class="text--secondary">북마크를 모두 불러왔습니다.</div>
      <div slot="no-results" class="text--secondary">북마크를 모두 불러왔습니다.</div>
    </Infinite-loading>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex';
import { CLEAR_BOOKMARKS, FETCH_MORE_BOOKMARKS, FETCH_TAG_WITH_BOOKMARKS } from '@/store/share/actionTypes.js';
import { BOOKMARKS, IS_BOOKMARKS_EMPTY } from '@/store/share/getterTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import BookmarkCard from '@/views/bookmark/component/BookmarkCard.vue';
import InfiniteLoading from 'vue-infinite-loading';

export default {
  name: 'BookmarkPage',
  components: {
    BookmarkCard,
    InfiniteLoading,
  },
  data() {
    return {
      page: 1,
      limit: 10,
      infiniteId: +new Date(),
      isInitialLoadingCompleted: false,
    };
  },
  beforeRouteUpdate(to, from, next) {
    this[CLEAR_BOOKMARKS]();
    next();
    this.changeTag();
  },
  created() {
    this.changeTag();
  },
  computed: {
    ...mapGetters([BOOKMARKS, IS_BOOKMARKS_EMPTY]),
  },
  methods: {
    ...mapActions([FETCH_TAG_WITH_BOOKMARKS, FETCH_MORE_BOOKMARKS, CLEAR_BOOKMARKS]),
    ...mapMutations([SHOW_SNACKBAR]),
    async infiniteHandler($state) {
      try {
        setTimeout(async () => {
          const bookmarks = await this[FETCH_MORE_BOOKMARKS]({
            tagId: this.$route.query.tag,
            offset: this.page,
            limit: this.limit,
          });
          if (bookmarks.length) {
            this.page += 1;
            $state.loaded();
          } else {
            $state.complete();
          }
        }, 500);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.BOOKMARK.FETCH.FAIL);
      }
    },
    async changeTag() {
      this.page = 1;
      this.infiniteId += 1;
      this.isInitialLoadingCompleted = false;
      const bookmarks = await this[FETCH_TAG_WITH_BOOKMARKS]({
        tagId: this.$route.query.tag,
        offset: this.page,
        limit: this.limit,
      });
      if (bookmarks.length) {
        this.page += 1;
      }
      this.isInitialLoadingCompleted = true;
    },
  },
};
</script>
