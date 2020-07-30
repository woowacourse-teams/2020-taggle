<template>
  <v-app id="container">
    <section>
      <img src="../assets/hashtag-1320568266489631024_24.png" alt="태그로고" />
      <h2 class="taggle-title">TAGGLE</h2>
      <Buttons :bookmarkId="bookmarkId" />
    </section>
    <TagInput :bookmarkId="bookmarkId" />
  </v-app>
</template>

<script>
import Buttons from '../components/Buttons.vue';
import TagInput from '../components/TagInput.vue';
import BookmarkService from '../api/module/bookmark.js';

export default {
  components: {
    Buttons,
    TagInput,
  },
  data() {
    return {
      bookmarkId: '',
      presentPage: {
        url: '',
      },
    };
  },
  methods: {
    getUrl: function() {
      chrome.tabs.query({ active: true, lastFocusedWindow: true }, async (tabs) => {
        this.presentPage.url = tabs[0].url;
        this.bookmarkId = await BookmarkService.save(this.presentPage);
      });
    },
  },
  mounted() {
    this.getUrl();
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
