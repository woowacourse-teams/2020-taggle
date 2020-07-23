<template>
  <div id="container">
    <section>
      <img src="../assets/hashtag-1320568266489631024_24.png" alt="태그로고" />
      <h2 class="taggle-title">TAGGLE</h2>
    </section>
    <TagInput />
  </div>
</template>

<script>
import TagInput from '../components/TagInput';

export default {
  components: {
    TagInput,
  },
  data() {
    return {};
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

.dropdown.is-hoverable:hover .dropdown-menu {
  display: block !important;
}

.taggle-title {
  display: inline;
  font-size: 1.5rem;
  font-weight: bold;
}
</style>
