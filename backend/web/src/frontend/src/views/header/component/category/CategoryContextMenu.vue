<template>
  <ContextMenu>
    <template slot="menuItems">
      <v-list-item v-for="(item, index) in items" :key="index" @click.prevent="item.action">
        <v-list-item-content>{{ item.content }}</v-list-item-content>
        <v-list-item-icon>
          <v-icon>
            {{ item.icon }}
          </v-icon>
        </v-list-item-icon>
      </v-list-item>
    </template>
  </ContextMenu>
</template>

<script>
import ContextMenu from '@/views/header/component/category/ContextMenu.vue';
import { DELETE_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import { mapActions, mapMutations } from 'vuex';

export default {
  name: 'CategoryContextMenu',
  components: {
    ContextMenu,
  },
  props: {
    category: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      items: [
        { content: '카테고리수정', icon: 'mdi-pencil', action: '' },
        {
          content: '카테고리삭제',
          icon: 'mdi-delete',
          action: this.onDeleteCategory,
        },
      ],
    };
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES, DELETE_CATEGORY]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onDeleteCategory() {
      try {
        await this[DELETE_CATEGORY](this.category.id);
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.DELETE.SUCCESS);
      } catch {
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.DELETE.FAIL);
      }
    },
  },
};
</script>

<style scoped></style>
