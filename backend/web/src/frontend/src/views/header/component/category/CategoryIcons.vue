<template>
  <v-list-item-icon>
    <CategoryEditModal :category="category" />
    <v-btn icon @click.prevent="onDeleteCategory">
      <v-icon>mdi-delete</v-icon>
    </v-btn>
  </v-list-item-icon>
</template>

<script>
import CategoryEditModal from '@/views/header/component/category/CategoryEditModal.vue';
import { mapActions, mapMutations } from 'vuex';
import { DELETE_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';

export default {
  name: 'CategoryIcons',
  components: {
    CategoryEditModal,
  },
  props: {
    category: {
      type: Object,
      required: true,
    },
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES, DELETE_CATEGORY]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onDeleteCategory() {
      try {
        await this[DELETE_CATEGORY](this.category.id);
        await this.fetchCategory();
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.DELETE.SUCCESS);
      } catch {
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.DELETE.FAIL);
      }
    },
    async fetchCategory() {
      await this[FETCH_CATEGORIES]();
    },
  },
};
</script>

<style scoped></style>
