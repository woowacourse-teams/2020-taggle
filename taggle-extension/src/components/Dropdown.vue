<template>
  <b-dropdown hoverable position="is-bottom-left" aria-role="list" class="is-pulled-right tag-lists">
    <b-icon icon="dots-vertical" slot="trigger"></b-icon>
    <b-dropdown-item v-for="item in items" class="tag-list" aria-role="listitem" :key="item.id">{{ item.value }}</b-dropdown-item>
  </b-dropdown>
</template>

<script>
export default {
  name: 'Dropdown-Menu',
  data() {
    return {
      items: [
        { id: 1, value: '북마크 제거' },
        { id: 2, value: 'Taggle 열기' },
      ],
    };
  },
};
</script>
