<script setup lang="ts">
import type { Organizer } from '@/types'

const modelValue = defineModel()
interface BaseSelectProps {
  label: string
  options: Organizer[]
}
const props = withDefaults(defineProps<BaseSelectProps>(), {
  label: ''
})
</script>
<template>
  <label v-if="label" class="block text-gray-500 font-bold">
    {{ props.label }}
  </label>
  <div class="relative w-1/4 mb-6 mx-auto">
    <select
      class="h-13 w-full pl-2.5 pr-6 text-xl align-middle appearance-none rounded-none border border-gray-400 focus:border-emerald-500 focus:outline-none"
      v-bind="$attrs"
      v-model="modelValue"
    >
      <option
        v-for="option in props.options"
        :key="option.id"
        :value="option.id"
        :selected="option.id === modelValue"
      >
        {{ option.name }}
      </option>
    </select>
    <svg
      class="pointer-events-none absolute right-3 top-1/2 -translate-y-1/2 w-2 h-2.5 fill-gray-700"
      viewBox="0 0 4 5"
    >
      <path d="M2 0L0 2h4zm0 5L0 3h4z" />
    </svg>
  </div>
</template>
