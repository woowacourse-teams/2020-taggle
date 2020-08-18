<template>
  <v-app id="container">
    <section>
      <img @click="moveTagglePage" src="../assets/hashtag-1320568266489631024_24.png" alt="tagLogo" />
      <h2 class="taggle-title">TAGGLE</h2>
      <Buttons
        @deleteBookmark="onDelete"
        @createBookmark="onCreate"
        v-if="isUrlLoaded"
        :hasBookmark="hasBookmark"
        :bookmarkUrl="url"
      />
    </section>
    <TagInput v-if="hasBookmark && isUrlLoaded" />
    <Snackbar />
  </v-app>
</template>

<script>
import Buttons from '@/components/Buttons.vue';
import TagInput from '@/components/TagInput.vue';
import Snackbar from '@/components/Snackbar.vue';

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
  created() {
    this.getUrl();
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
      chrome.tabs.query({ active: true, currentWindow: true }, function(tabs) {
        chrome.tabs.executeScript(tabs[0].id, { code: `window.location.href='https://taggle.kr';` });
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

.dropdown.is-hoverable:hover .dropdown-menu {
  display: block !important;
}

.taggle-title {
  display: inline;
  font-size: 1.5rem;
  font-weight: bold;
}
</style>
