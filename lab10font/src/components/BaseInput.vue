<script setup lang="ts">
const modelValue = defineModel()
interface BaseInputProps {
  label: string
}
const props = withDefaults(defineProps<BaseInputProps>(), {
  label: ''
})
</script>
<template>
  <label v-if="label">
    {{ props.label }}
  </label>
  <input class="h-13 w-1/4 px-2.5 text-xl border border-gray-400 focus:border-emerald-500 focus:outline-none mb-6" v-bind="$attrs" v-model="modelValue" :placeholder="label" />
</template>
