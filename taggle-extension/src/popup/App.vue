<template>
  <v-app id="container">
    <section>
      <v-row class="ma-0" align="center">
        <img @click="moveTagglePage" src="../assets/hashtag-1320568266489631024_24.png" alt="tagLogo" width="24" height="24"/>
        <h2 class="ml-1">TAGGLE</h2>
        <v-spacer />
        <Buttons
          @deleteBookmark="onDelete"
          @createBookmark="onCreate"
          v-if="isUrlLoaded"
          :hasBookmark="hasBookmark"
          :bookmarkUrl="url"
        />
      </v-row>
    </section>
    <TagInput v-if="hasBookmark && isUrlLoaded" />
    <Snackbar />
  </v-app>
</template>

<script>
import Buttons from '@/components/Buttons.vue';
import TagInput from '@/components/TagInput.vue';
import Snackbar from '@/components/Snackbar.vue';
import UserService from '@/api/module/user.js';
import { SERVICE_URL } from '@/utils/constants.js';

export default {
  components: {
    Snackbar,
    Buttons,
    TagInput,
  },
  data() {
    return {
      hasBookmark: true,
      url: '',
    };
  },
  computed: {
    isUrlLoaded() {
      return !!this.url;
    },
  },
  async created() {
    try {
      await UserService.loggedIn();
      this.getUrl();
    } catch (e) {
      chrome.tabs.create({ url: SERVICE_URL });
      window.close();
    }
  },
  methods: {
    onCreate() {
      this.hasBookmark = true;
    },
    onDelete() {
      this.hasBookmark = false;
    },
    getUrl() {
      chrome.tabs.query({ active: true, lastFocusedWindow: true }, async (tabs) => {
        this.url = tabs[0].url;
      });
    },
    moveTagglePage() {
      chrome.tabs.query({ active: true, currentWindow: true }, (tabs) => {
        chrome.tabs.executeScript(tabs[0].id, { code: `window.location.href='${SERVICE_URL}';` });
      });
    },
  },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Roboto+Condensed');

html,
body {
  font-family: 'Roboto Condensed', sans-serif;
  width: 300px;
}

#container {
  padding: 10px 12px;
}

.v-application--wrap {
  min-height: 0 !important;
}
</style>
