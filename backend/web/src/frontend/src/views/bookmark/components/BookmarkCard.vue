<template>
  <v-row class="mt-10 layout-container">
    <v-col cols="10">
      <v-container grid-list-lg text-xs-center>
        <CardStream :bookmarks="bookmarks" v-if="flexOption" />
        <CardModule :bookmarks="bookmarks" v-else />
      </v-container>
    </v-col>
    <v-col cols="2">
      <v-icon @click="isOption('stream')" x-large>view_stream</v-icon>
      <v-icon @click="isOption('module')" x-large>view_module</v-icon>
    </v-col>
  </v-row>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';
import { FETCH_BOOKMARKS } from '@/store/share/actionType';
import CardStream from './CardStream.vue';
import CardModule from './CardModule.vue';

export default {
  name: 'BookmarkCard',
  components: {
    CardStream,
    CardModule,
  },
  created() {
    this.$store.dispatch(FETCH_BOOKMARKS);
    console.log(this.bookmarks);
  },
  computed: {
    ...mapGetters(['bookmarks']),
  },
  data() {
    return {
      flexOption: true,
    };
  },
  methods: {
    ...mapActions([FETCH_BOOKMARKS]),
    isOption(type) {
      this.flexOption = type === 'stream';
    },
  },
};
</script>

<style scoped>
.layout-container {
  width: 100%;
}
</style>
