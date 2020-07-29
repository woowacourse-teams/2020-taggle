<template>
  <v-app id="container">
    <section>
      <img src="../assets/hashtag-1320568266489631024_24.png" alt="태그로고" />
      <h2 class="taggle-title">TAGGLE</h2>
      <Buttons />
    </section>
    {{ url }}
    <TagInput />
  </v-app>
</template>

<script>
import Buttons from '../components/Buttons';
import TagInput from '../components/TagInput';

export default {
  components: {
    Buttons,
    TagInput,
  },
  data() {
    return {
      url: '',
    };
  },
  methods: {
    getUrl: function() {
      chrome.tabs.query({ active: true, lastFocusedWindow: true }, function(tabs) {
        this.url = tabs[0].url;
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
