<template>
  <b-dropdown hoverable position="is-bottom-left" aria-role="list" class="is-pulled-right tag-lists">
    <b-icon icon="dots-vertical" slot="trigger"></b-icon>
    <b-dropdown-item v-for="item in items" class="tag-list" aria-role="listitem" :key="it">{{ item }}</b-dropdown-item>
  </b-dropdown>
</template>

<script>
export default {
  name: 'Dropdown-Menu',
  data() {
    return {
      items: ['북마크 제거', 'Taggle 열기'],
    };
  },
};
</script>
