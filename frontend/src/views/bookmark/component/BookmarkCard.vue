<template>
  <v-row>
    <v-col>
      <CardStream v-if="hasTagId" :bookmarks="totalBookmarks" v-show="cardDisplayMode === 'stream'" />
      <CardStream v-else :bookmarks="bookmarks" v-show="cardDisplayMode === 'stream'" />
      <CardModule v-if="hasTagId" :bookmarks="totalBookmarks" v-show="cardDisplayMode === 'module'" />
      <CardModule v-else :bookmarks="bookmarks" v-show="cardDisplayMode === 'module'" />
    </v-col>
    <v-col cols="1">
      <div class="card-change-button-container">
        <v-icon @click="changeCardDisplayMode('stream')" x-large>view_stream</v-icon>
        <v-icon @click="changeCardDisplayMode('module')" x-large>view_module</v-icon>
      </div>
    </v-col>
  </v-row>
</template>

<script>
import { mapGetters } from 'vuex';
import CardStream from '@/views/bookmark/component/CardStream.vue';
import CardModule from '@/views/bookmark/component/CardModule.vue';
import { BOOKMARKS, TOTAL_BOOKMARKS } from '@/store/share/getterTypes.js';

export default {
  name: 'BookmarkCard',
  components: {
    CardStream,
    CardModule,
  },
  props: {
    hasTagId: Boolean,
  },
  computed: {
    ...mapGetters([BOOKMARKS, TOTAL_BOOKMARKS]),
  },
  data() {
    return {
      cardDisplayMode: 'stream',
    };
  },
  methods: {
    changeCardDisplayMode(cardType) {
      this.cardDisplayMode = cardType;
    },
  },
};
</script>

<style scoped>
.card-change-button-container {
  position: fixed;
}
</style>
