<template>
  <ContextMenu>
    <template slot="menuItems">
      <v-list-item v-for="(item, index) in items" :key="index" @click.prevent="item.action">
        <v-list-item-title>{{ item.title }}</v-list-item-title>
        <v-list-item-action>
          <v-icon>
            {{ item.icon }}
          </v-icon>
        </v-list-item-action>
      </v-list-item>
    </template>
  </ContextMenu>
</template>

<script>
import ContextMenu from '@/views/header/component/category/ContextMenu.vue';
import TagService from '@/api/module/tag.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import { mapActions, mapMutations } from 'vuex';
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';

export default {
  name: 'TagContextMenu',
  components: {
    ContextMenu,
  },
  props: {
    tag: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      items: [
        { title: '카테고리 변경', icon: 'mdi-pencil', action: '' },
        { title: '태그 삭제', icon: 'mdi-delete', action: this.onDeleteTag },
      ],
    };
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onDeleteTag() {
      try {
        await TagService.delete(this.tag.id);
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR](MESSAGES.TAG.DELETE.SUCCESS);
      } catch {
        this[SHOW_SNACKBAR](MESSAGES.TAG.DELETE.FAIL);
      }
    },
  },
};
</script>
