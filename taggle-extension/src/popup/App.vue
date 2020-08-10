<template>
  <v-app id="container">
    <section>
      <img src="../assets/hashtag-1320568266489631024_24.png" alt="tagLogo" />
      <h2 class="taggle-title">TAGGLE</h2>
      <Buttons
        @deleteBookmark="onDelete"
        @createBookmark="onCreate"
        v-if="isUrlLoaded"
        :hasBookmark="hasBookmark"
        :bookmarkUrl="url"
      />
    </section>
    <TagInput v-if="hasBookmark && isUrlLoaded" :bookmarkUrl="url" />
  </v-app>
</template>

<script>
import Buttons from '../components/Buttons.vue';
import TagInput from '../components/TagInput.vue';

export default {
  components: {
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
      return this.url !== '';
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

.taggle-title {
  display: inline;
  font-size: 1.5rem;
  font-weight: bold;
}
</style>
