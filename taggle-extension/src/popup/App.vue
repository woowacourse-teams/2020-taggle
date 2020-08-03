<template>
  <v-app id="container">
    <section>
      <img src="../assets/hashtag-1320568266489631024_24.png" alt="태그로고" />
      <h2 class="taggle-title">TAGGLE</h2>
      <Buttons
        @toggleDeleteBookmark="onToggle"
        v-if="isUrlLoaded"
        :isNotDeletedBookmark="isNotDeletedBookmark"
        :bookmarkUrl="url"
      />
    </section>
    <TagInput v-if="isNotDeletedBookmark && isUrlLoaded" :bookmarkUrl="url" />
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
  computed: {
    isUrlLoaded() {
      return this.url !== '';
    },
  },
  data() {
    return {
      isNotDeletedBookmark: true,
      url: '',
    };
  },
  created() {
    this.getUrl();
  },
  methods: {
    onToggle() {
      this.isNotDeletedBookmark = !this.isNotDeletedBookmark;
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
