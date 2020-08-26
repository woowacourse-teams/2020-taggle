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
    <template slot="menuDialog">
      <ConfirmDialog ref="confirm" />
    </template>
  </ContextMenu>
</template>

<script>
import { DELETE_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import { mapActions, mapMutations } from 'vuex';
import ContextMenu from '@/views/header/component/category/ContextMenu.vue';
import ConfirmDialog from '@/views/common/component/ConfirmDialog.vue';

export default {
  name: 'CategoryContextMenu',
  components: {
    ContextMenu,
    ConfirmDialog,
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
        { content: '카테고리수정', icon: 'mdi-pencil', action: this.onEditCategory },
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
      const confirm = await this.$refs.confirm.open('카테고리 삭제', '정말로 해당 카테고리를 삭제하시겠습니까?');
      if (!confirm) {
        return;
      }
      try {
        await this[DELETE_CATEGORY](this.category.id);
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.DELETE.SUCCESS);
      } catch {
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.DELETE.FAIL);
      }
    },
    onEditCategory() {
      this.$emit('edit-category');
    },
  },
};
</script>
