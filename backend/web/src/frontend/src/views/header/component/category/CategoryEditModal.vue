<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on" class="mx-2" small icon>
        <v-icon dark>mdi-pencil</v-icon>
      </v-btn>
    </template>

    <v-card>
      <v-app-bar dense flat>
        <v-card-title>
          카테고리 수정
        </v-card-title>
        <v-spacer></v-spacer>
        <v-btn icon @click="dialog = false">
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-app-bar>

      <v-card-text class="pt-6">
        <v-form ref="category" @submit.prevent="onEditCategory">
          <v-text-field
            v-model="category.title"
            dense
            :rules="rules"
            label="이름을 입력후에 enter를 입력하여 저장."
          ></v-text-field>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapActions, mapMutations } from 'vuex';
import { EDIT_CATEGORY, FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import validator from '@/utils/validator.js';

export default {
  name: 'CategoryAddModal',
  data() {
    return {
      rules: validator.category.title,
      dialog: false,
    };
  },
  props: {
    category: {
      type: Object,
      required: true,
    },
  },
  methods: {
    ...mapActions([EDIT_CATEGORY, FETCH_CATEGORIES]),
    ...mapMutations([SHOW_SNACKBAR]),
    async onEditCategory() {
      if (!this.$refs.category.validate()) {
        return;
      }
      try {
        await this[EDIT_CATEGORY](this.category);
        await this[FETCH_CATEGORIES]();
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.EDIT.SUCCESS);
        this.dialog = false;
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.CATEGORY.EDIT.FAIL);
      }
    },
  },
};
</script>
