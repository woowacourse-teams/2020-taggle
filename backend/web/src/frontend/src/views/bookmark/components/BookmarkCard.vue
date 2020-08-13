<template>
  <v-row class="mt-10 layout-container">
    <v-col cols="10">
      <v-container grid-list-lg text-xs-center>
        <CardStream :bookmarks="bookmarks" v-show="cardDisplayMode === 'stream'" />
        <CardModule :bookmarks="bookmarks" v-show="cardDisplayMode === 'module'" />
      </v-container>
    </v-col>
    <v-col cols="2">
      <v-icon @click="changeCardDisplayMode('stream')" x-large>view_stream</v-icon>
      <v-icon @click="changeCardDisplayMode('module')" x-large>view_module</v-icon>
    </v-col>
  </v-row>
</template>

<script>
import { mapGetters } from 'vuex';
import CardStream from '@/views/bookmark/components/CardStream.vue';
import CardModule from '@/views/bookmark/components/CardModule.vue';
import { BOOKMARKS } from '@/store/share/getterTypes.js';

export default {
  name: 'BookmarkCard',
  components: {
    CardStream,
    CardModule,
  },
  computed: {
    ...mapGetters([BOOKMARKS]),
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
.layout-container {
  width: 100%;
}
</style>
