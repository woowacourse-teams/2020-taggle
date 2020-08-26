<template>
  <v-dialog v-model="dialog" width="500">
    <v-card>
      <v-app-bar dense flat>
        <v-card-title>카테고리 변경</v-card-title>
        <v-spacer></v-spacer>
        <v-btn icon @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-app-bar>

      <v-card-text class="pt-6">
        <v-form ref="category" @submit.prevent="onChangeCategory">
          <v-select
            v-model="categoryId"
            :items="allCategoriesForSelect"
            dense
            outlined
            label="변경하려는 카테고리를 선택해주세요."
          ></v-select>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import { EDIT_TAG, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { ALL_CATEGORIES_FOR_SELECT } from '@/store/share/getterTypes.js';
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'CategoryEditModal',
  props: {
    tag: {
      type: Object,
      required: true,
    },
    editDialog: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      dialog: false,
      categoryId: '',
    };
  },
  watch: {
    editDialog(value) {
      if (value) {
        this.dialog = value;
      }
    },
    dialog() {
      if (!this.dialog) {
        this.$emit('close');
      }
    },
  },
  computed: {
    ...mapGetters([ALL_CATEGORIES_FOR_SELECT]),
  },
  methods: {
    ...mapActions([EDIT_TAG, FETCH_CATEGORIES]),
    async onChangeCategory() {
      try {
        await this[EDIT_TAG]({
          categoryId: this.categoryId,
          tagId: this.tag.id,
        });
      } catch (e) {
        //
      }
    },
  },
};
</script>
