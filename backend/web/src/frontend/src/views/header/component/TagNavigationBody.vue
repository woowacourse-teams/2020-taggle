<template>
  <v-card :flat="true">
    <v-list v-for="category in categories" :key="category.id" class="grow">
      <CategoryGroup :category="category" />
    </v-list>
  </v-card>
</template>

<script>
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { mapActions, mapGetters } from 'vuex';
import { SEARCHED_CATEGORIES, TOTAL_CATEGORIES } from '@/store/share/getterTypes.js';
import CategoryGroup from '@/views/header/component/CategoryGroup.vue';

export default {
  name: 'TagNavigationBody',
  components: {
    CategoryGroup,
  },
  props: {
    searchKeyword: {
      type: String,
    },
  },
  computed: {
    ...mapGetters([TOTAL_CATEGORIES, SEARCHED_CATEGORIES]),
    categories() {
      if (this.searchKeyword === '') {
        return this[TOTAL_CATEGORIES];
      }
      return this[SEARCHED_CATEGORIES](this.searchKeyword);
    },
  },
  created() {
    this[FETCH_CATEGORIES]();
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES]),
  },
};
</script>

<style>
.v-list {
  padding: 0 !important;
}
.v-list-group__header {
  padding: 4px !important;
}
.v-list-item {
  padding: 0 20px !important;
}
</style>
