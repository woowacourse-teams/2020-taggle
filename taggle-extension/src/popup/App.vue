<template>
  <div id="container">
    <div>
      <section>
        <img src="../assets/hashtag-1320568266489631024_24.png" alt="" />
        <h2 class="taggle-title">TAGGLE</h2>
        <b-dropdown hoverable position="is-bottom-left" aria-role="list" class="is-pulled-right tag-lists">
          <b-icon icon="dots-vertical" slot="trigger"></b-icon>
          <b-dropdown-item class="tag-list" aria-role="listitem">북마크 제거</b-dropdown-item>
          <b-dropdown-item class="tag-list" aria-role="listitem">Taggle 열기</b-dropdown-item>
        </b-dropdown>
      </section>
      <vue-tags-input v-model="tag" :tags="tags" @tags-changed="newTags => (tags = newTags)" placeholder="태그 추가" />
    </div>
  </div>
</template>

<script>
import VueTagsInput from '@johmun/vue-tags-input';
export default {
  components: {
    VueTagsInput,
  },
  data() {
    return {
      tag: '',
      tags: [],
    };
  },
};
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Roboto+Condensed');

html,
body {
  font-family: 'Roboto Condensed', sans-serif;
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
