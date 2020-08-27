<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on" class="mx-2" small icon>
        <v-icon dark>mdi-cog</v-icon>
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
        <v-card class="mx-auto" max-width="100%" tile>
          <v-list v-for="{ id, title, tags } in categories" :key="id" class="grow">
            <v-list-group class="text-left mt-2" link value="true">
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title class="font-weight-black text-h7">{{ title }}</v-list-item-title>
                </v-list-item-content>
              </template>
              <draggable
                class="list-group"
                :list="tags"
                group="categoryTag"
                handle=".handle"
                @change="changeTag($event, id)"
              >
                <v-list-item v-for="{ id, name } in tags" :key="id">
                  <v-list-item-icon>
                    <v-icon class="handle">mdi-format-align-justify</v-icon>
                  </v-list-item-icon>
                  <v-list-item-content>
                    <span class="text">{{ name }} </span>
                  </v-list-item-content>
                </v-list-item>
              </draggable>
            </v-list-group>
          </v-list>
        </v-card>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script>
import draggable from 'vuedraggable';
import { TOTAL_CATEGORIES } from '@/store/share/getterTypes.js';
import { mapActions, mapGetters, mapMutations } from 'vuex';
import { EDIT_TAG } from '@/store/share/actionTypes.js';
import { MESSAGES } from '@/utils/constants.js';
import { SHOW_SNACKBAR } from '@/store/share/mutationTypes.js';

export default {
  name: 'TagEditModal',
  components: {
    draggable,
  },
  data() {
    return {
      dialog: false,
    };
  },
  computed: {
    ...mapGetters([TOTAL_CATEGORIES]),
  },
  methods: {
    ...mapActions([EDIT_TAG]),
    ...mapMutations([SHOW_SNACKBAR]),
    async changeTag({ added }, tagId) {
      try {
        await this[EDIT_TAG](tagId, added.element.id);
        this[SHOW_SNACKBAR](MESSAGES.TAG.EDIT.SUCCESS);
      } catch (e) {
        this[SHOW_SNACKBAR](MESSAGES.TAG.EDIT.FAIL);
      }
    },
  },
};
</script>
